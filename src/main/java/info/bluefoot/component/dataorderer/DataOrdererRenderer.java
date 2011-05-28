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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Renderer for {@link DataOrderer}. Will render something like:
 * 
 * <pre>
 * &lt;h:link value="Column" outcome="/your/current/view" includeViewParams="true"&gt;
 *   &lt;f:param name="sort" value="column" /&gt;
 *   &lt;f:param name="order" value="asc" /&gt;
 * &lt;/h:link&gt;
 * </pre>
 * @author bluefotot
 *
 */
@FacesRenderer(rendererType = "info.bluefoot.DataOrderer", 
        componentFamily = "info.bluefoot.component")
public class DataOrdererRenderer extends Renderer {
    
    private static final Logger log = LoggerFactory.getLogger(DataOrdererRenderer.class);

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if(log.isDebugEnabled()) {
            log.debug("encoding DataOrdererRender");
        }
        
        // ~ Getting the tools  ==========================================
        
        DataOrderer dataOrderer = (DataOrderer) component;
        ResponseWriter writer = context.getResponseWriter();
        LazyDataModel model = dataOrderer.getModel();

        // ~ Root element  ===============================================
        
        writer.startElement("span", dataOrderer);
        writer.writeAttribute("id", dataOrderer.getClientId(context), "id");

        // ~ Writing css class ===========================================
        
        String spanClass = "";
        if(dataOrderer.getField().equals(model.getSortField())) {
            if(model.getSortOrder()) {
                spanClass = dataOrderer.getAscStyleClass();
            } else {
                spanClass = dataOrderer.getDescStyleClass();
            }
        }
        if(dataOrderer.getStyleClass()!=null) {
            spanClass = String.format("%s %s", spanClass, dataOrderer.getStyleClass());
        }
        writer.writeAttribute("class", spanClass, "styleClass");
        
        // ~ Encoding link and parameters  =================================
        
        HtmlOutcomeTargetLink link = new HtmlOutcomeTargetLink();
        link.setIncludeViewParams(true);
        link.setValue(dataOrderer.getValue());
        link.setOutcome(context.getViewRoot().getViewId());
        
        UIParameter parSort = new UIParameter();
        parSort.setName("sort");
        parSort.setValue(dataOrderer.getField().toString());
        
        UIParameter parOrder = new UIParameter();
        parOrder.setName("order");
        if(dataOrderer.getField().equals(model.getSortField())) {
            parOrder.setValue(model.getSortOrder() ? "desc" : "asc");
        } else {
            parOrder.setValue("asc");
        }
        
        link.getChildren().add(parSort);
        link.getChildren().add(parOrder);
        link.encodeBegin(context);
        link.encodeChildren(context);
        link.encodeEnd(context);

        // ~ Closing root element ===========================================
        
        writer.endElement("span");
    }
}
