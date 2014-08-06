package one.async.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OneAsyncUtils {

	public static <GPOutput> boolean isMapComplete(
			final Map<Integer, GPOutput> map, final int size) {
		for (int i = 0; i < size; i++) {
			if (map.get(Integer.valueOf(i)) == null) {
				return false;
			}
		}
		return true;
	}

	public static <GOutput> List<GOutput> toOrderedList(
			final Map<Integer, GOutput> responseMap) {
		final List<GOutput> localResponses = new ArrayList<GOutput>(
				responseMap.size());
		for (int i = 0; i < responseMap.size(); i++) {
			final GOutput rr = responseMap.get(Integer.valueOf(i));

			assert rr != null;

			localResponses.add(rr);
		}
		return localResponses;
	}

}
