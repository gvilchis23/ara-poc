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

package com.broadleafcommerce.demo.dao;

import org.broadleafcommerce.common.i18n.domain.Translation;
import org.broadleafcommerce.common.i18n.domain.TranslationImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@Repository("blPrivateDemoDao")
public class PrivateDemoDaoImpl implements PrivateDemoDao {
    
    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;
    
    @Override
    public List<Translation> readAllTranslations() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Translation> criteria = builder.createQuery(Translation.class);
        Root<TranslationImpl> translation = criteria.from(TranslationImpl.class);

        criteria.select(translation);

        TypedQuery<Translation> query = em.createQuery(criteria);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
