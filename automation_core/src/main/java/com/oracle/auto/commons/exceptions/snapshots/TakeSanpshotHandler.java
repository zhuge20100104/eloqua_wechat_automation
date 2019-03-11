package com.oracle.auto.commons.exceptions.snapshots;

import java.util.List;

/**
 * Created by zhous5 on 2017/7/3.
 */

public interface TakeSanpshotHandler {
	void onBeforeTakeSnapshot(Object page);
	void onAfterTakenSnapshot(Object page, List<String> snapshots);
}
