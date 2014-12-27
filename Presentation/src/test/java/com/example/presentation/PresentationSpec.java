package com.example.presentation;

import android.content.Context;

import com.example.shared.dagger.DaggerSupport;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

@RunWith(PresentationRobolectricTestRunner.class)
public abstract class PresentationSpec {

    public DomainModuleMock domainModuleMock;
    public Context context;

    @Before
    public void provideRobolectricContextReference() {
        context = Robolectric.application;
    }

    @Before
    public void mockDomainModule() {
        domainModuleMock = new DomainModuleMock();
        DaggerSupport.init(domainModuleMock);
    }
}