package bd.edu.seu.busseatallocationsystem.UI.BookingView;

import bd.edu.seu.busseatallocationsystem.Model.Booking;
import bd.edu.seu.busseatallocationsystem.Model.Bus;
import bd.edu.seu.busseatallocationsystem.Service.BookingService;
import bd.edu.seu.busseatallocationsystem.Service.BusService;
import bd.edu.seu.busseatallocationsystem.UI.BusView.BusForm;
import bd.edu.seu.busseatallocationsystem.UI.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "booking",layout = MainView.class)
@PageTitle("BSAS | Booking")
public class BookingList extends VerticalLayout {

    Grid<Booking> Grid = new Grid<>(Booking.class);
    TextField textField = new TextField();
    TextField phone = new TextField();
    ComboBox<Bus> busNumber = new ComboBox<>();
    IntegerField numberOfBooking = new IntegerField();
    Button delete = new Button("Delete");
    Button updateList = new Button("Update List");
    Button addBooking = new Button("ADD BOOKING");
    private final BookingForm bookingForm;

    private BookingService bookingService;
    private BusService busService;
    private List<Bus> buses;
    public BookingList(BookingService bookingService, BusService busService) {
        this.bookingService = bookingService;
        this.busService = busService;
        addClassName("list");
        setSizeFull();
        configureGrid();
        buses = busService.findAll();
        bookingForm = new BookingForm(buses,bookingService,busService);
        Div div = new Div(Grid, bookingForm);
        div.setSizeFull();
        div.addClassName("Content");
        add(ToolBar(),deleteBar(),div);
        updateGrid();
        closeEditor();
    }

    private Component deleteBar() {
        phone.setPattern("[0-9]*");
        phone.setPreventInvalidInput(true);
        phone.setPlaceholder("Phone");
        busNumber.setPlaceholder("Select one");
        busNumber.setItems(buses);
        busNumber.setItemLabelGenerator(Bus::getBusNumber);
        numberOfBooking.setPlaceholder("Canceling Number");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(click-> deleteBooking());
        VerticalLayout layout = new VerticalLayout(new Label("Cancel Booking"),new HorizontalLayout(phone,busNumber,numberOfBooking,delete));
        layout.addClassName("Toolbar");
        return layout;
    }

    public void updateGrid() {

        String value = textField.getValue();
        if(value.isEmpty())
        {
            Grid.setItems(bookingService.findAll());
        }else{
            Grid.setItems(bookingService.findByPhoneNumber(value));
        }

    }

    private void configureGrid() {
        Grid.addClassName("Grid");
        Grid.setSizeFull();
        Grid.setColumns("name","busNumber","date","paymentType","seatNumber");
        Grid.getColumns().forEach(col-> col.setAutoWidth(true));
    }

    int v = 1;
    private void EditBooking() {
        if(v==1) {
            bookingForm.setVisible(true);
            addClassName("editing");
            v = 0;
        }else {
            closeEditor();
            v = 1;
        }
    }

    public void closeEditor() {
        bookingForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout ToolBar() {

        textField.setPlaceholder("Filter by Phone Number....");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e-> updateGrid());

        addBooking.addClickListener(click-> EditBooking());

        updateList.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        updateList.addClickListener(click-> UpdateList());
        HorizontalLayout layout = new HorizontalLayout(textField,addBooking,updateList);
        layout.addClassName("Toolbar");
        return layout;
    }

    private void UpdateList() {
        updateGrid();
        closeEditor();
    }

    private void deleteBooking() {
        if(phone.getValue()!="" && busNumber.getValue()!=null && numberOfBooking.getValue()!=null) {
            List<Booking> bookings = bookingService.findByPhoneNumberAndBusNumber(phone.getValue(),busNumber.getValue().getBusNumber());
            int booking = bookings.size();
            if (numberOfBooking.getValue() <= booking) {
                bookingService.DeleteBooking(phone.getValue(),busNumber.getValue().getBusNumber(),numberOfBooking.getValue());
                Bus bus = busService.findByBusNumber(bookings.get(0).getBusNumber());
                bus.setNumberofSeatAreFree(( bus.getNumberofSeatAreFree()+numberOfBooking.getValue()));
                busService.CreateBus(bus);
                updateGrid();
                Dialog dialog = new Dialog();
                dialog.add(new Label("Delete Successful"));
                dialog.setWidth("100px");
                dialog.setHeight("100px");
                dialog.open();
                phone.setValue("");
                busNumber.setValue(null);
            } else {
                Dialog dialog = new Dialog();
                dialog.add(new Label("You just have only "+(booking)+" seat bookings in "+busNumber.getValue().getBusNumber()+" bus, so you can't cancel more then that"));
                dialog.setWidth("100px");
                dialog.setHeight("100px");
                dialog.open();
            }
        }else {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Complete The From.."));
            dialog.setWidth("100px");
            dialog.setHeight("100px");
            dialog.open();
        }
    }




}
