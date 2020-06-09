package bd.edu.seu.busseatallocationsystem.UI.BusView;

import bd.edu.seu.busseatallocationsystem.Model.Bus;
import bd.edu.seu.busseatallocationsystem.Service.BusService;
import bd.edu.seu.busseatallocationsystem.UI.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAnyRole('admin')")
@Route(value = "admin/access",layout = MainView.class)
@PageTitle("BSAS | ADMIN")
public class BusList extends VerticalLayout {

    Grid<Bus> Grid = new Grid<>(Bus.class);
    TextField textField = new TextField();
    Button addBus = new Button("ADD BUS");
    private final BusForm busForm;

    private BusService busService;

    public BusList(BusService busService) {
        this.busService = busService;
        addClassName("list");
        setSizeFull();
        configureGrid();
        busForm = new BusForm();

        busForm.addListener(BusForm.SaveEvent.class,this::saveBus);
        busForm.addListener(BusForm.DeleteEvent.class, this::deleteBus);
        busForm.addListener(BusForm.closeEvent.class, event-> closeEditor());



        Div div = new Div(Grid, busForm);
        div.setSizeFull();
        div.addClassName("Content");
        add(ToolBal(),div);
        updateGrid();
        closeEditor();
    }

    private  void deleteBus(BusForm.DeleteEvent event) {
        busService.DeleteBus(event.getBus());
        updateGrid();
        closeEditor();
        Dialog dialog = new Dialog();
        dialog.add(new Label("Delete Successful"));
        dialog.setWidth("100px");
        dialog.setHeight("100px");
        dialog.open();
    }

    private  void saveBus(BusForm.SaveEvent event) {
        busService.CreateBus(event.getBus());
        updateGrid();
        closeEditor();
        Dialog dialog = new Dialog();
        dialog.add(new Label("Save Successful"));
        dialog.setWidth("100px");
        dialog.setHeight("100px");
        dialog.open();
    }

    private void updateGrid() {

        String value = textField.getValue();
        if(value.isEmpty())
        {
            Grid.setItems(busService.findAll());
        }else{
            Grid.setItems(busService.findByBusNumber(value));
        }

    }

    private void configureGrid() {
        Grid.addClassName("Grid");
        Grid.setSizeFull();
        Grid.setColumns("busNumber","staffName","boardingPoint","time","numberofSeatAreFree","costPerSeat");
        Grid.getColumns().forEach(col-> col.setAutoWidth(true));
        Grid.asSingleSelect().addValueChangeListener(event-> EditBus(event.getValue()));
    }

    private void EditBus(Bus value) {
        if(value==null)
        {
            closeEditor();
        }else
        {
            busForm.setBus(value);
            busForm.setVisible(true);
            addClassName("editing");
        }

    }

    private void closeEditor() {
        busForm.setBus(null);
        busForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout ToolBal() {
        textField.setPlaceholder("Filter by Bus Number....");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e-> updateGrid());

        addBus.addClickListener(click-> SaveBus());
        HorizontalLayout layout = new HorizontalLayout(textField,addBus);
        layout.addClassName("Toolbar");
        return layout;
    }

    private void SaveBus() {
        Grid.asSingleSelect().clear();
        EditBus(new Bus());
    }
}
