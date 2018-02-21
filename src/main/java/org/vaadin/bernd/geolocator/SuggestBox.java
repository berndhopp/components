package org.vaadin.bernd.geolocator;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.HasFilterableDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static java.util.stream.Collectors.toList;

@Tag("paper-dropdown-input")
@HtmlImport("addon/suggest/suggestbox.html")
public class SuggestBox extends PolymerTemplate<SuggestBox.SuggestModel> implements HasFilterableDataProvider<String, String> {

    private DataProvider<String, String> dataProvider;

    protected SuggestBox(DataProvider<String, String> dataProvider){
        setDataProvider(dataProvider);
    }

    @Override
    public void setDataProvider(DataProvider<String, String> dataProvider) {

        if(dataProvider == null){
            throw new IllegalArgumentException("dataprovider must not be null");
        }

        this.dataProvider = dataProvider;
    }

    @Override
    public <C> void setDataProvider(DataProvider<String, C> dataProvider, SerializableFunction<String, C> filterConverter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setItems(Collection<String> items) {
        throw new UnsupportedOperationException();
    }

    public interface SuggestModel extends TemplateModel{
        void setSuggestions(List<String> suggestions);
    }

    private List<String> getSuggestions(String input){
        if (input == null) {
            return EMPTY_LIST;
        }

        return dataProvider.fetch(new Query<>(input)).collect(toList());
    }

    @EventHandler
    private void onValueChanged(@EventData("event.detail.value") String newValue) {
        getModel().setSuggestions(newValue == null ? EMPTY_LIST : getSuggestions(newValue));
    }
}
