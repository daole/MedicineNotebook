package com.dreamdigitizers.drugmanagement.views.implementations.fragments.screens;

public abstract class ScreenEntry extends Screen {
    @Override
    public boolean shouldPopBackStack() {
        return true;
    }
}
