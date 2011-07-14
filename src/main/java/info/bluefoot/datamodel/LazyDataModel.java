/*
 * Copyright 2011 http://bluefoot.info
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.bluefoot.datamodel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.model.DataModel;

/**
 * Lazy implementation for {@link DataModel}. <br />
 * A single instance of {@link LazyDataModel} can handle:
 * <ul>
 * <li>the total count of the rows ({@link #getTotalRowsCount()})</li>
 * <li>the size of the page (i.e. the number of rows fetched on each request) (
 * {@link #getPageSize()})</li>
 * <li>the current page index that is showed at the moment (
 * {@link #getCurrentPage()})</li>
 * <li>the rows ({@link #getData()})</li>
 * <li>the current row selected index ({@link #getRowIndex()})</li>
 * <li>the total number of the pages (based upon, of course, in the page size
 * and the total number of rows) ({@link #getNumberOfPages()})</li>
 * <li>a specific field for sorting (will be <tt>null</tt> by default) (
 * {@link #getSortField()})</li>
 * <li>a sort orientation ({@link #getSortOrder()})</li>
 * </ul>
 * It is designed to be used in a request-based bean, so in every request, the
 * data will be lazily loaded.
 * 
 * @author bluefoot
 * @param <T> the type of object (often an database entity) that this instance
 *            is handling
 */
public abstract class LazyDataModel<T> extends DataModel<T> implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 3;
    
    private Integer totalRowsCount;
    private Integer pageSize;
    private Integer currentPage;
    private List<T> data;
    private Integer rowIndex;
    private Integer numberOfPages;
    private String sortField;
    private Boolean sortOrder;
    private Map<String, String> filters;

    // ~ Constructor ========================================================
    
    /**
     * Creates an instance with a page size of {@link #DEFAULT_PAGE_SIZE}.
     */
    public LazyDataModel() {
        this.pageSize = LazyDataModel.DEFAULT_PAGE_SIZE;
        this.currentPage = 1;
        sortOrder = true;
    }
    
    // ~ Getters/setters ====================================================

    /**
     * Gets the number of pages of the query, based on {@link #getPageSize()}
     */
    public Integer getNumberOfPages() {
        if(this.numberOfPages==null) {
            this.numberOfPages = (int) Math.ceil(getTotalRowsCount() * 1d / pageSize);;
        }
        return this.numberOfPages;
    }
    
    public Integer getTotalRowsCount() {
        if(this.totalRowsCount==null) {
            this.totalRowsCount = count(this.filters);
        }
        return this.totalRowsCount;
    }

    public void setTotalRowsCount(Integer totalRowsCount) {
        this.totalRowsCount = totalRowsCount;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    
    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Boolean sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Object getWrappedData() {
        if (this.data == null) {
            this.data = this.load((this.currentPage-1)*this.pageSize, this.pageSize, this.sortField, this.sortOrder, this.filters);
        }
        return this.data;
    }

    @Override
    public int getRowCount() {
        if(this.data == null) {
            return -1;
        }
        return this.data.size();
    }

    /**
     * Forces a refreshment of the data. Basic calls {@link #load(Integer, Integer, String, Boolean, Map)},
     * then {@link #count(Map)}.
     */
    public void refreshData() {
        this.data = this.load((this.currentPage-1)*this.pageSize, this.pageSize, this.sortField, this.sortOrder, this.filters);
        this.totalRowsCount = count(this.filters);
        this.numberOfPages = (int) Math.ceil(getTotalRowsCount() * 1d / pageSize);;
    }

    @Override
    public T getRowData() {
        return this.data.get(getRowCount());
    }

    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    @Override
    public boolean isRowAvailable() {
        if(this.data == null) {
            return false;
        }
        return this.rowIndex >= 0 && this.rowIndex < this.data.size();
    }

    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setWrappedData(Object data) {
        this.data = (List<T>) data;
    }

    public Map<String, String> getFilters() {
        return filters;
    }
    
    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }
    
    // ~ Actions ===========================================================

    /**
     * Removes an item of the in-memory-listing (i.e. only works for an item
     * in the current page)
     */
    public void deleteItem(T item) {
        this.data.remove(item);
    }
    
    /**
     * Loads a page of data.
     * @param first first element to be loaded
     * @param pageSize number of elements to be loaded
     * @param sortField field so sort data with
     * @param sortOrder ordering of the filter. <tt>true</tt> for ascendant. <tt>false</tt> for descendant.
     * @param filters key/value of the filters
     * @return a list with the data found. no more than <tt>pageSize</tt> elements will be loaded.
     */
    public abstract List<T> load(Integer first, Integer pageSize, String sortField, Boolean sortOrder, Map<String, String> filters);
    
    /**
     * Counts how many items will be loaded with the given filters. Will not be
     * bigger than {@link #pageSize}.
     * @param filters key/value of the filters
     * @return how many items found with the filters
     */
    public abstract Integer count(Map<String, String> filters);
}
