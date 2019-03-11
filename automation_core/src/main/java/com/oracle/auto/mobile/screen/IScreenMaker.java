package com.oracle.auto.mobile.screen;

public interface IScreenMaker {
    <T extends ScreenBase> T screenAs(Class<T> screenClass, Object... initArgs);
}
