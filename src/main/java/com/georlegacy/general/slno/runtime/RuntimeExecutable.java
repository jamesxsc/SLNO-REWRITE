package com.georlegacy.general.slno.runtime;

import com.georlegacy.general.slno.runtime.enumeration.StopLevel;

public abstract class RuntimeExecutable {

    protected abstract int firstStart(SLNORuntime runtime);

    protected abstract int start(SLNORuntime runtime);

    protected abstract int stop(StopLevel stopLevel);

}
