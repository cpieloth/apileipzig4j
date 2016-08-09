package de.apileipzig.mediahandbook.entity;

import de.apileipzig.mediahandbook.DefaultEntityFactory;
import de.apileipzig.mediahandbook.EntityFactory;
import org.junit.Assert;
import org.junit.Test;

public class DefaultEntityFactoryTest {
    @Test
    public void createCompany() throws Exception {
        EntityFactory ef = new DefaultEntityFactory();
        Company company = ef.createCompany();

        Assert.assertNotNull(company);
        Assert.assertTrue(company instanceof DefaultCompany);
    }
}
