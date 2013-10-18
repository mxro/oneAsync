/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package one.async.joiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import one.async.utils.IdentityArrayList;
import one.async.utils.OneAsyncUtils;

/**
 * This utility class supports in writing Java in a more asynchronous style.
 * This class allows to <i>collect</i> the results from various callbacks, which
 * result from calling a method on all elements of a list. The callback provided
 * by this class will only be called if all the callbacks for the individual
 * list elements have been executed.
 * 
 * @author Max Rohde
 * 
 * @param <GInput>
 * @param <GOutput>
 */
public class ListCallbackJoiner<GInput, GOutput> {
	final Map<Integer, GOutput> responseMap;
	final List<GInput> messages;
	final int expectedSize;
	final ListCallback<GOutput> callback;

	public LocalCallback<GOutput> createCallback(final GInput message) {
		return new LocalCallback<GOutput>() {

			@Override
			public void onSuccess(final GOutput response) {
				synchronized (responseMap) {
					responseMap.put(messages.indexOf(message), response);

					if (OneAsyncUtils.isMapComplete(responseMap, expectedSize)) {
						final List<GOutput> localResponses = OneAsyncUtils
								.toOrderedList(responseMap);

						callback.onSuccess(localResponses);
					}
				}
			}

			Boolean failureReported = false;

			@Override
			public void onFailure(final Throwable t) {
				// failure should be reported only once.
				synchronized (failureReported) {
					if (!failureReported) {
						callback.onFailure(t);
					} else {
						throw new RuntimeException(t); // TODO maybe disable ...
					}
					failureReported = true;
				}
			}

		};
	}

	public ListCallbackJoiner(final List<GInput> messages,
			final ListCallback<GOutput> callback) {
		super();
		this.messages = new IdentityArrayList<GInput>(messages);
		this.responseMap = new HashMap<Integer, GOutput>();
		expectedSize = messages.size();
		this.callback = callback;

		if (expectedSize == 0) {
			this.callback.onSuccess(new ArrayList<GOutput>(0));
		}
	}

}
