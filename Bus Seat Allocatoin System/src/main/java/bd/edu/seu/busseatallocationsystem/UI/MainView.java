package bd.edu.seu.busseatallocationsystem.UI;

import bd.edu.seu.busseatallocationsystem.UI.BookingView.BookingList;
import bd.edu.seu.busseatallocationsystem.UI.BusView.BusList;
import bd.edu.seu.busseatallocationsystem.UI.BusView.UserBusList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink routerLink = new RouterLink("Bus List", UserBusList.class);
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(
                new VerticalLayout(
                        routerLink,
                        new RouterLink("Booking List", BookingList.class)
                )
        );
    }

    private void createHeader() {
        H1 logo = new H1("Bus Seat Allocation System");
        logo.addClassName("logo");
        Anchor anchor = new Anchor("/logout","Log Out");
        HorizontalLayout horizontalLayout = new HorizontalLayout(new DrawerToggle(),logo,anchor);
        horizontalLayout.expand(logo);
        horizontalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        horizontalLayout.setWidth("100%");
        horizontalLayout.addClassName("header");
        addToNavbar(horizontalLayout);
    }
}
