/*
 * #%L
 * BroadleafCommerce Workflow
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * NOTICE:  All information contained herein is, and remains
 * the property of Broadleaf Commerce, LLC
 * The intellectual and technical concepts contained
 * herein are proprietary to Broadleaf Commerce, LLC
 * and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Broadleaf Commerce, LLC.
 * #L%
 */
package com.broadleafcommerce.index;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.exception.ExceptionHelper;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.site.service.SiteService;
import org.broadleafcommerce.core.search.service.solr.SolrIndexService;
import org.springframework.stereotype.Service;

import com.broadleafcommerce.jobsevents.domain.SystemEvent;
import com.broadleafcommerce.jobsevents.service.AbstractSystemEventConsumer;

import java.io.IOException;

import javax.annotation.Resource;

/**
 * @author Jeff Fischer
 */
@Service("blDemoIndexSystemEventConsumer")
public class DemoIndexSystemEventConsumer extends AbstractSystemEventConsumer {

    private static final Log LOG = LogFactory.getLog(DemoIndexSystemEventConsumer.class);

    @Resource(name = "blSolrIndexService")
    protected SolrIndexService mtsis;

    @Resource(name = "blSiteService")
    protected SiteService siteService;

    @Override
    public void consumeEvent(final SystemEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Solr indexing for site: " + event.getEventDetails().get("site").getValue());
        }
        try {
            mtsis.rebuildIndex();
        } catch (ServiceException e) {
            throw ExceptionHelper.refineException(e);
        } catch (IOException e) {
            throw ExceptionHelper.refineException(e);
        }
    }

    @Override
    public String getEventType() {
        return "blPrivateDemoIndexEvent";
    }

}
