package org.vaadin.bernd.geolocator;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.component.notification.Notification.Position.BOTTOM_END;

@Route("")
public class DemoView extends Div {

    public DemoView() {
        final GeoLocator geoLocator = new GeoLocator();

        geoLocator.addPositionCallback(new GeoLocator.PositionCallback() {
            @Override
            public void onPosition(double longitude, double latitude) {
                Notification.show("location is " + longitude + " / " + latitude, 1000, BOTTOM_END);
            }

            @Override
            public void onGeoError() {

            }
        });

        add(geoLocator);
    }
}
