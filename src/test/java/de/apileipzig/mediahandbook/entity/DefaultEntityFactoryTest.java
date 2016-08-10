package de.apileipzig.mediahandbook.entity;

import org.junit.Assert;
import org.junit.Test;

public class DefaultEntityFactoryTest {
    @Test
    public void createCompany() throws Exception {
        EntityFactory ef = new DefaultEntityFactory();
        Company company = ef.newCompany();

        Assert.assertNotNull(company);
        Assert.assertTrue(company instanceof DefaultCompany);
    }
}
