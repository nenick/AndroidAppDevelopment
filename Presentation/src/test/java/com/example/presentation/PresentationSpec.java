package com.example.presentation;

import com.example.shared.dagger.DaggerSupport;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(PresentationRobolectricTestRunner.class)
public abstract class PresentationSpec {

    public DomainModuleMock domainModuleMock;

    @Before
    public void mockDomainModule() {
        domainModuleMock = new DomainModuleMock();
        DaggerSupport.init(domainModuleMock);
    }
}