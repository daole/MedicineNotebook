package com.dreamdigitizers.medicinenote.views.implementations.fragments.screens;

public abstract class ScreenEntry extends Screen {
    @Override
    public boolean shouldPopBackStack() {
        return true;
    }
}
