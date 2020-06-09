package bd.edu.seu.busseatallocationsystem.UI.BusView;

import bd.edu.seu.busseatallocationsystem.Model.Bus;
import bd.edu.seu.busseatallocationsystem.Service.BusService;
import bd.edu.seu.busseatallocationsystem.UI.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "",layout = MainView.class)
@PageTitle("BSAS | User Bus")
public class UserBusList extends VerticalLayout {
    com.vaadin.flow.component.grid.Grid<Bus> Grid = new Grid<>(Bus.class);
    TextField textField = new TextField();
    private BusService busService;

    public UserBusList(BusService busService) {
        this.busService = busService;
        addClassName("list");
        setSizeFull();
        configureGrid();

        Div div = new Div(Grid);
        div.setSizeFull();
        div.addClassName("Content");
        add(ToolBal(),div);
        updateGrid();
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
    }

    private HorizontalLayout ToolBal() {
        textField.setPlaceholder("Filter by Bus Number....");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e-> updateGrid());
        RouterLink routerLink = new RouterLink("Go To The Admin Panel",BusList.class);
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());
        HorizontalLayout layout = new HorizontalLayout(textField);
        layout.addClassName("Toolbar");
        return layout;
    }
}
