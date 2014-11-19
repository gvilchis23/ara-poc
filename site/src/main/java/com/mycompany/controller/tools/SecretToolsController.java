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

package com.mycompany.controller.tools;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;
import org.broadleafcommerce.core.search.service.solr.SolrIndexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/tools")
public class SecretToolsController extends BroadleafAbstractController {
    
    @Resource(name = "blSolrIndexService")
    protected SolrIndexService mtsis;
    
    protected static final String key = "81e924bc0346f81122a4e8382fa277680acc4613";

    @RequestMapping("/solr/rebuild")
    public @ResponseBody String rebuildAllIndexes(HttpServletRequest request, HttpServletResponse response, Model model) 
            throws ServiceException, IOException {
        validateKey(request);
        mtsis.rebuildIndex();
        return "All indexes rebuilt.";
    }
    
    protected void validateKey(HttpServletRequest request) {
        String key = request.getParameter("key");
        if (!SecretToolsController.key.equals(key)) {
            throw new RuntimeException("Incorrect security key");
        }
    }

}
