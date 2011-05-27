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
package info.bluefoot.component.datanavigator;

import info.bluefoot.datamodel.LazyDataModel;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;


/**
 * Displays a navigator for a {@link LazyDataModel}, based upon it's properties
 * (current page, number of pages, size, etc). 
 * 
 * @author bluefoot
 * 
 */
@FacesComponent("info.bluefoot.DataNavigator")
public class DataNavigator extends UIPanel {
    
    private static final String DEFAULT_RENDERER = "info.bluefoot.DataNavigator";
    public static final String COMPONENT_FAMILY = "info.bluefoot.component";

    protected enum PropertyKeys {
        styleClass, innerStyleClass, model;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public DataNavigator() {
        setRendererType(DEFAULT_RENDERER);
    }
    
    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
    
    // ~ Getter/Setter ======================================================
    
    public String getStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }
    
    public void setStyleClass(String styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, styleClass);
    }
    
    public String getInnerStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.innerStyleClass, null);
    }
    
    public void setInnerStyleClass(String innerStyleClass) {
        getStateHelper().put(PropertyKeys.innerStyleClass, innerStyleClass);
    }
    
    public LazyDataModel getModel() {
        return (LazyDataModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(LazyDataModel model) {
        getStateHelper().put(PropertyKeys.model, model);
    }
    
}
