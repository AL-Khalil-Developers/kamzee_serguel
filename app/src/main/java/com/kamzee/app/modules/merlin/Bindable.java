package com.kamzee.app.modules.merlin;

public interface Bindable extends Registerable {
    void onBind(NetworkStatus networkStatus);
}
