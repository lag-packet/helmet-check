package com.helmetcheck;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class HelmetCheckOverlay extends Overlay
{
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();
    private String displayText = "unequipped";

    @Inject
    public HelmetCheckOverlay(Client client)
    {
        this.client = client;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add(TitleComponent.builder()
                .text(displayText)
                .color(Color.WHITE)
                .build());

        // Center the panel
        Dimension panelSize = panelComponent.render(graphics);
        int x = (client.getCanvasWidth() - panelSize.width) / 2;
        int y = (client.getCanvasHeight() - panelSize.height) / 2;
        panelComponent.setPreferredLocation(new Point(x, y));

        panelComponent.render(graphics);

        return null;
    }

    public void updateHelmetStatus()
    {
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);

        if (equipment == null)
        {
            displayText = "unequipped";
            return;
        }

        boolean helmetEquipped = equipment.getItem(EquipmentInventorySlot.HEAD.getSlotIdx()) != null;

        displayText = helmetEquipped ? "equipped" : "unequipped";

        // Debugging: print to console to ensure this code is running
        System.out.println("Helmet status updated: " + displayText);
    }
}
