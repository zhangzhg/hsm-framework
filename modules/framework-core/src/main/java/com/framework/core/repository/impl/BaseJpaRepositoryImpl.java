package com.framework.core.repository.impl;

import com.framework.common.domain.SearchFilter;
import com.framework.common.util.ParamUtils;
import com.framework.core.enums.StatusEnum;
import com.framework.core.listener.IDeleteListenable;
import com.framework.core.repository.BaseJpaRepository;
import com.framework.core.support.query.DynamicSpecifications;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.Serializable;
import java.util.*;

/**
 * 自定义Repository实现
 * (放所有Repository都需要用到的方法)
 *
 * @param <T>  实体
 * @param <ID> id
 */
public class BaseJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseJpaRepository.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager; // jpa 实体管理器

    public BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public BaseJpaRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    @Transactional
    public int deleteByIds(ID[] ids) {
        //逻辑删除
        if (IDeleteListenable.class.isAssignableFrom(getDomainClass())) {
            List<ID> list = Arrays.asList(ids);
            List<T> entities = this.findAll(list);
            for (T entity : entities) {
                ((IDeleteListenable) entity).setStatus(StatusEnum.DELETED.toString());
            }
            int result =  this.save(entities).size();
            return result;
        } else {
            for(ID id : ids){
                delete(id);
            }
            return ids.length;
        }
    }

    @Override
    public List<T> find(List<SearchFilter> searchFilters) {
        Specification<T> spec = DynamicSpecifications.bySearchFilter(searchFilters);
        return this.findAll(spec);
    }

    @Override
    public Page<T> find(List<SearchFilter> searchFilters, Pageable page) {
        Specification<T> spec = DynamicSpecifications.bySearchFilter(searchFilters);
        return this.findAll(spec, page);
    }

    @Override
    public List<T> find(T entity) {
        Specification<T> spec = getSpecification(entity);
        return this.findAll(spec);
    }

    @Override
    public Page<T> find(T entity, Pageable page) {
        Specification<T> spec = getSpecification(entity);
        return this.findAll(spec, page);
    }

    @Override
    public T findOne(T entity) {
        Specification<T> spec = getSpecification(entity);
        return this.findOne(spec);
    }

    private Specification<T> getSpecification(T entity) {
        List<SearchFilter> searchFilters = SearchFilter.convertBean(entity);
        return DynamicSpecifications.bySearchFilter(searchFilters);
    }

    /**
     * 根据特定条件删除实体
     */
    @Transactional
    public int deleteInBatch(T object) {
        String hql = QueryUtils.getQueryString("DELETE FROM %s x", this.getDomainClass().getSimpleName());
        StringBuilder sb = new StringBuilder(hql);
        sb.append(" WHERE ");
        Map map = BeanMap.create(object);
        Set<String> keys = map.keySet();
        boolean firstAttrFlag = true;
        for (String key : keys) {
            String value = (String) map.get(key);
            if (StringUtils.isNoneBlank(value)) {
            	if(!firstAttrFlag) {
            		sb.append(" AND ");
            	}
                sb.append(String.format("%s.%s = '%s'", new Object[]{"x", key, value}));
                firstAttrFlag = false;
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("deleteInBatch parameter: {}", StringUtils.join(object, ","));
        }
        return this.entityManager.createQuery(sb.toString()).executeUpdate();
    }

    /**
     * 根据特定条件批量删除实体
     */
    @Transactional
    public int deleteInBatch(List<T> objects) {
    	if(ParamUtils.isEmpty(objects)) {
    		return 0;
    	}
    	if(objects.size()==1) {
    		return deleteInBatch(objects.get(0));
    	}
        String hql = QueryUtils.getQueryString("DELETE FROM %s x", this.getDomainClass().getSimpleName());
        StringBuilder sb = new StringBuilder(hql);
        sb.append(" WHERE ");
        for(Iterator<T> objectIter = objects.iterator();objectIter.hasNext();) {
        	T object = objectIter.next();
        	sb.append("(");
	        Map map = BeanMap.create(object);
	        boolean firstAttrFlag = true;
	        Set<String> keys = map.keySet();
	        for (Iterator<String> iter = keys.iterator();iter.hasNext();) {
	        	String key = iter.next();
	            String value = (String) map.get(key);
	            if (StringUtils.isNoneBlank(value)) {
	            	if(!firstAttrFlag) {
	            		sb.append(" AND ");
	            	}
	                sb.append(String.format("%s.%s = '%s'", new Object[]{"x", key, value}));
	                firstAttrFlag = false;
	            }
	        }
	        sb.append(")");
	        if(objectIter.hasNext()) {
	        	sb.append(" OR ");
	        }
        }
        if (logger.isInfoEnabled()) {
            logger.info("deleteInBatch parameter: {}", StringUtils.join(objects, ","));
        }
        return this.entityManager.createQuery(sb.toString()).executeUpdate();
    }


	@Override
	public void delete(Iterable<? extends T> entities) {
		if(ParamUtils.isNull(entities))
			return;
        List<String> idList = new ArrayList<>();
        for(Iterator<? extends T> iterator = entities.iterator();iterator.hasNext();) {
            T object = iterator.next();
            Map beanMap = BeanMap.create(object);
            idList.add((String) beanMap.get("id"));
        }
        this.deleteByIds((ID[]) idList.toArray(new String[idList.size()]));
	}

}
