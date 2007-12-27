/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.spring;

import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOComponent;
import org.mule.umo.endpoint.UMOImmutableEndpoint;

public class InheritedPropertiesMule2458TestCase extends FunctionalTestCase
{

    protected String getConfigResources()
    {
        return "/org/mule/test/spring/inherited-properties-mule-2458-test.xml";
    }

    public void testProperties()
    {
        UMOComponent component = managementContext.getRegistry().lookupComponent("service");
        assertNotNull(component);
        UMOImmutableEndpoint endpoint = (UMOImmutableEndpoint) component.getInboundRouter().getEndpoints().get(0);
        assertNotNull(endpoint);

        assertProperty(endpoint, "global-only", "global");
        assertProperty(endpoint, "local-only", "local");
        assertProperty(endpoint, "url-only", "url");

        assertProperty(endpoint, "global-and-local", "local");
        assertProperty(endpoint, "global-and-url", "global");
        assertProperty(endpoint, "local-and-url", "local");
        
        assertProperty(endpoint, "all", "local");
    }

    protected void assertProperty(UMOImmutableEndpoint endpoint, String name, String value)
    {
        Object property = endpoint.getProperty(name);
        assertNotNull("Property " + name + " is missing", property);
        String actual = property.toString();
        assertEquals("Unexpected value for " + name + ": " + actual + ", not " + value, value, actual);
    }

}