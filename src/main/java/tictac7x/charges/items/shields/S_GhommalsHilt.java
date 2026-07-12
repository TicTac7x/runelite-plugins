package tictac7x.charges.items.shields;

import tictac7x.charges.TicTac7xChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.OnVarbitChanged;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Provider;
import tictac7x.charges.store.ids.ItemId;
import tictac7x.charges.store.ids.VarbitId;

import java.util.List;

public class S_GhommalsHilt extends ChargedItem {
    public S_GhommalsHilt(final Provider provider) {
        super(TicTac7xChargesImprovedConfig.ghommals_hilt, ItemId.GHOMMALS_HILT_1, provider);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemId.GHOMMALS_HILT_1).maxCharges(3),
            new TriggerItem(ItemId.GHOMMALS_HILT_2).maxCharges(5),
            new TriggerItem(ItemId.GHOMMALS_HILT_3).unlimitedCharges(),
            new TriggerItem(ItemId.GHOMMALS_HILT_4).maxCharges(3),
            new TriggerItem(ItemId.GHOMMALS_HILT_5).maxCharges(5),
            new TriggerItem(ItemId.GHOMMALS_HILT_6).unlimitedCharges(),
        };

        this.triggers.addAll(List.of(
            new OnVarbitChanged(VarbitId.GHOMMALS_HILT_TROLLHEIM_TELEPORTS_USED).varbitValueConsumer(varbitValue -> {
                switch (itemId) {
                    case ItemId.GHOMMALS_HILT_1:
                        setCharges(3 - varbitValue);
                        break;
                    case ItemId.GHOMMALS_HILT_2:
                        setCharges(5 - varbitValue);
                        break;
                }
            }),
            new OnVarbitChanged(VarbitId.GHOMMALS_HILT_MORULREK_TELEPORTS_USED).varbitValueConsumer(varbitValue -> {
                switch (itemId) {
                    case ItemId.GHOMMALS_HILT_4:
                        setCharges(3 - varbitValue);
                        break;
                    case ItemId.GHOMMALS_HILT_5:
                        setCharges(5 - varbitValue);
                        break;
                }
            })
        ));
    }
}
