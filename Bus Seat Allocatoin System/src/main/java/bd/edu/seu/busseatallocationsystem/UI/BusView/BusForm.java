package bd.edu.seu.busseatallocationsystem.UI.BusView;

import bd.edu.seu.busseatallocationsystem.Model.Bus;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;


public class BusForm extends FormLayout {

    TextField busNumber = new TextField("Bus Number");
    TextField staffName = new TextField("Staff Name");
    TextField boardingPoint = new TextField("Boarding Point");
    TextField time = new TextField("Time");
    IntegerField numberofSeatAreFree = new IntegerField("Seats Are Free");
    IntegerField costPerSeat = new IntegerField("Cost per Seat");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    Binder<Bus> binder = new BeanValidationBinder<>(Bus.class);
    public BusForm() {
        addClassName("form");
        binder.bindInstanceFields(this);
        add(
                busNumber,
                staffName,
                boardingPoint,
                time,
                numberofSeatAreFree,
                costPerSeat,
                buttonConfigure()
        );


    }

    public void setBus(Bus bus)
    {
        binder.setBean(bus);
    }
    private Component buttonConfigure() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);


        save.addClickListener(event-> ValidAndSave());
        delete.addClickListener(event-> fireEvent(new DeleteEvent(this,binder.getBean())));
        cancel.addClickListener(event-> fireEvent(new closeEvent(this)));


        binder.addStatusChangeListener(event->save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,delete,cancel);
    }

    private void ValidAndSave() {
        if(busNumber.getValue()!="" && staffName.getValue()!=""&& boardingPoint.getValue()!=""&& time.getValue()!=""&&costPerSeat.getValue()!=0&&numberofSeatAreFree.getValue()!=0)
        {
            fireEvent(new SaveEvent(this,binder.getBean()));
        }else
        {
            Dialog dialog = new Dialog();
            dialog.add(new Label("Complete the Form"));

            dialog.setWidth("100px");
            dialog.setHeight("100px");
            dialog.open();
        }
    }


    public static abstract class BusFormEvent extends ComponentEvent<BusForm>
    {
        private Bus bus;
        public BusFormEvent(BusForm source, Bus bus) {
            super(source, false);
            this.bus = bus;
        }

        public Bus getBus()
        {
            return bus;
        }
    }

    public static class SaveEvent extends BusFormEvent
    {

        public SaveEvent(BusForm source, Bus bus) {
            super(source, bus);
        }
    }
    public static class DeleteEvent extends BusFormEvent
    {

        public DeleteEvent(BusForm source, Bus bus) {
            super(source, bus);
        }
    }

    public static class closeEvent extends BusFormEvent {
        public closeEvent(BusForm source) {
            super(source,null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener)
    {
        return getEventBus().addListener(eventType, listener);
    }

}
