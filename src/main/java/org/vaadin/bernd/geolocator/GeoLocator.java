package org.vaadin.bernd.geolocator;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Tag("geo-locator")
@HtmlImport("addon/geolocator/geolocator.html")
public class GeoLocator extends PolymerTemplate<TemplateModel> {

    public interface PositionCallback{
        void onPosition(double longitude, double latitude);
        void onGeoError();
    }

    private final List<PositionCallback> positionCallbacks = new ArrayList<>();

    public void addPositionCallback(PositionCallback positionCallback){
        requireNonNull(positionCallback);

        positionCallbacks.add(positionCallback);
    }

    @EventHandler
    private void onGeoResponse(@EventData("event.detail.longitude") double longitude, @EventData("event.detail.latitude") double latitude) {
        positionCallbacks.forEach(consumer -> consumer.onPosition(longitude, latitude));
    }

    @EventHandler
    private void onGeoError() {
        positionCallbacks.forEach(PositionCallback::onGeoError);
    }
}
