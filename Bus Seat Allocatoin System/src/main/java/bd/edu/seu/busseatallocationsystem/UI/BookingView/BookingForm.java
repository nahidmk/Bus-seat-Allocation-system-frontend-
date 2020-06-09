package bd.edu.seu.busseatallocationsystem.UI.BookingView;

import bd.edu.seu.busseatallocationsystem.Model.Booking;
import bd.edu.seu.busseatallocationsystem.Model.Bus;
import bd.edu.seu.busseatallocationsystem.Model.PaymentTypeEnum;
import bd.edu.seu.busseatallocationsystem.Service.BookingService;
import bd.edu.seu.busseatallocationsystem.Service.BusService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class BookingForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField phone = new TextField("Phone");
    ComboBox<Bus> busNumber = new ComboBox<>("Bus Number");
    IntegerField bookingNumber = new IntegerField("Number of Booking");
    DatePicker datePicker = new DatePicker("Date");
    ComboBox<PaymentTypeEnum> paymentType = new ComboBox<>("Payment Type");
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");


    private BookingService service;
    private BusService busService;


    public BookingForm(List<Bus> buses, BookingService service, BusService busService)
    {
        this.service = service;
        this.busService = busService;
        addClassName("form");
        phone.setPattern("[0-9]*");
        phone.setPreventInvalidInput(true);
        phone.setMaxLength(11);
        paymentType.setItems(PaymentTypeEnum.values());
        busNumber.setItems(buses);
        busNumber.setPlaceholder("Select one");
        paymentType.setPlaceholder("Select one");
        busNumber.setItemLabelGenerator(Bus::getBusNumber);
        add(
                busNumber,
                name,
                phone,
                bookingNumber,
                datePicker,
                paymentType,
                buttonConfigure()
        );

    }

    private Component buttonConfigure() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        save.addClickListener(event-> SaveBooking());
        cancel.addClickListener(event-> cancelBooking());

        return new HorizontalLayout(save,cancel);
    }

    private void cancelBooking() {
        name.setValue("");
        phone.setValue("");
        bookingNumber.setValue(0);
        busNumber.setValue(null);
        datePicker.setValue(null);
        paymentType.setValue(null);
    }

    private void SaveBooking() {
        if(name.getValue()!="" && phone.getValue()!="" && bookingNumber.getValue()!=null && busNumber.getValue()!=null && datePicker.getValue()!=null && paymentType.getValue()!=null) {
            int numberOfBooking = bookingNumber.getValue();
            String busNum = busNumber.getValue().getBusNumber();
            int seatAreFree = busService.findByBusNumber(busNum).getNumberofSeatAreFree();
            int seatAreBooked = service.findByBusNumber(busNum).size();
            if (seatAreFree >= numberOfBooking) {
                for (int i = 1; i <=numberOfBooking; i++) {
                    Booking booking = new Booking();
                    booking.setName(name.getValue());
                    booking.setPhone(phone.getValue());
                    booking.setBusNumber(busNum);
                    booking.setPaymentType(paymentType.getValue().name());
                    booking.setDate(datePicker.getValue());
                    booking.setSeatNumber(busNum+"."+(seatAreBooked+i));
                    service.CreateBooking(booking);
                }
                Bus bus = busService.findByBusNumber(busNum);
                bus.setNumberofSeatAreFree(seatAreFree-numberOfBooking);
                busService.CreateBus(bus);
                Dialog dialog = new Dialog();
                dialog.add(new Label("Save Successful"));
                dialog.setWidth("100px");
                dialog.setHeight("100px");
                dialog.open();
            } else {
                Dialog dialog = new Dialog();
                dialog.add(new Label("sorry..Only " + seatAreFree + " Seats are free"));
                dialog.setWidth("100px");
                dialog.setHeight("100px");
                dialog.open();
            }
        }else{
            Dialog dialog = new Dialog();
            dialog.add(new Label("Complete The From.."));
            dialog.setWidth("400px");
            dialog.setHeight("150px");
            dialog.open();
        }
    }

}
