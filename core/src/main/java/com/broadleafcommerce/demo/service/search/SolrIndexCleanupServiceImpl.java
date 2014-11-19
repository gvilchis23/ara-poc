/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.broadleafcommerce.demo.service.search;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.config.RuntimeEnvironmentPropertiesManager;
import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.core.search.service.solr.SolrIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.annotation.Resource;


/**
 * This class holds methods that are used to clean up parts of the Solr index specifically associated with the private 
 * demo functionality.
 * 
 * @author Andre Azzolini (apazzolini)
 */
@Service("pdSolrIndexCleanupService")
public class SolrIndexCleanupServiceImpl implements SolrIndexCleanupService {
    protected static final Log LOG = LogFactory.getLog(SolrIndexCleanupServiceImpl.class);

    protected static final String DDL_PROP = "blPU.hibernate.hbm2ddl.auto";
    protected static final String[] QUAL_VALUES = new String[] { "create", "create-drop" };

    @Resource(name = "blSolrIndexService")
    protected SolrIndexService mtsis;

    @Autowired
    protected RuntimeEnvironmentPropertiesManager propMgr;
    
    @Override
    public void rebuildIndexAtStartupIfNecessary() throws ServiceException, IOException {
        String propVal = propMgr.getProperty(DDL_PROP).toLowerCase();
        if (ArrayUtils.contains(QUAL_VALUES, propVal)) {
            mtsis.rebuildIndex();
            LOG.info("All indexes rebuilt at startup because value was " + propVal);
        } else {
            LOG.info("Not rebuilding indexes because value was " + propVal);
        }
    }

}
