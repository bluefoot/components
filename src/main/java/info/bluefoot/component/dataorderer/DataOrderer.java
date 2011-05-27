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
package info.bluefoot.component.dataorderer;

import info.bluefoot.datamodel.LazyDataModel;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;


/**
 * Displays a link for sorting a field on a {@link LazyDataModel}.
 * 
 * @author bluefoot
 * 
 */
@FacesComponent("info.bluefoot.DataOrderer")
public class DataOrderer extends UIPanel {
    
    private static final String DEFAULT_RENDERER = "info.bluefoot.DataOrderer";
    public static final String COMPONENT_FAMILY = "info.bluefoot.component";

    protected enum PropertyKeys {
        styleClass, model, field, value, ascStyleClass, descStyleClass;

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

    public DataOrderer() {
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
    
    public LazyDataModel getModel() {
        return (LazyDataModel) getStateHelper().eval(PropertyKeys.model, null);
    }

    public void setModel(LazyDataModel model) {
        getStateHelper().put(PropertyKeys.model, model);
    }
    
    public Object getField() {
        return (Object) getStateHelper().eval(PropertyKeys.field, null);
    }

    public void setField(Object field) {
        getStateHelper().put(PropertyKeys.field, field);
    }

    public String getValue() {
        return (String) getStateHelper().eval(PropertyKeys.value, null);
    }
    
    public void setValue(String value) {
        getStateHelper().put(PropertyKeys.value, value);
    }
    
    public String getAscStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.ascStyleClass, null);
    }
    
    public void setAscStyleClass(String ascStyleClass) {
        getStateHelper().put(PropertyKeys.ascStyleClass, ascStyleClass);
    }
    
    public String getDescStyleClass() {
        return (String) getStateHelper().eval(PropertyKeys.descStyleClass, null);
    }
    
    public void setDescStyleClass(String descStyleClass) {
        getStateHelper().put(PropertyKeys.descStyleClass, descStyleClass);
    }
    
}
