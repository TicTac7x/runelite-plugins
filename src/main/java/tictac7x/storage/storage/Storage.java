package tictac7x.storage.storage;

import java.util.*;
import java.util.regex.Pattern;

public class Storage {
    public final int itemContainerId;
    protected final Map<Integer, StorageItem> storage = new LinkedHashMap<>();
    private int slotsUsed = 0;
    private final List<Runnable> listeners = new ArrayList<>();

    public Storage(final int itemContainerId) {
        this.itemContainerId = itemContainerId;
    }

    public void addItems(final List<StorageItem> items) {
        storage.clear();
        slotsUsed = 0;

        for (final StorageItem item : items) {
            addItem(item);
        }

        notifyListeners();
    }

    protected void addItem(final StorageItem item) {
        if (storage.containsKey(item.id)) {
            storage.get(item.id).increaseQuantity(item.getQuantity());
        } else {
            storage.put(item.id, item);
        }

        slotsUsed++;
    }

    public int getSlotsUsed() {
        return slotsUsed;
    }

    public List<StorageItem> getItems() {
        return new ArrayList<>(storage.values());
    }

    public List<StorageItem> getItems(final String visibleString, final String hiddenString, final boolean usePrioritize) {
        final String[] visibleList = visibleString.replaceAll("\\*", ".*").split(",");
        final String[] hiddenList = hiddenString.replaceAll("\\*", ".*").split(",");

        final List<StorageItem> startsWithItems = new ArrayList<>();
        final List<StorageItem> containsWordItems = new ArrayList<>();
        final List<StorageItem> otherItems = new ArrayList<>();

        for (final StorageItem item : storage.values()) {
            if (!isItemHidden(item, hiddenList, !usePrioritize) && isItemVisible(item, visibleList, !usePrioritize)) {
                if (usePrioritize && itemStartsWith(item, visibleList, !usePrioritize)) {
                    startsWithItems.add(item);
                } else if (usePrioritize && itemContains(item, visibleList, !usePrioritize)) {
                    containsWordItems.add(item);
                } else {
                    otherItems.add(item);
                }
            }
        }

        final List<StorageItem> allItems = new ArrayList<>();
        allItems.addAll(startsWithItems);
        allItems.addAll(containsWordItems);
        allItems.addAll(otherItems);
        return allItems;
    }

    private boolean itemContains(final StorageItem item, final String[] visibleList, final boolean caseSensitive) {
        for (final String visibleString : visibleList) {
            final Pattern pattern = Pattern.compile("\\b" + Pattern.quote(visibleString) + "\\b", caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(item.name).find()) {
                return true;
            }
        }

        return false;
    }

    private boolean isItemVisible(final StorageItem item, final String[] visibleList, final boolean caseSensitive) {
        if (visibleList.length == 0 || (visibleList.length == 1 && visibleList[0].isEmpty())) return true;

        for (final String visibleString : visibleList) {
            final Pattern pattern = Pattern.compile(visibleString, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(item.name).find()) {
                return true;
            }
        }

        return false;
    }

    private boolean itemStartsWith(final StorageItem item, final String[] visibleList, final boolean caseSensitive) {
        for (final String visibleString : visibleList) {
            final Pattern pattern = Pattern.compile("^" + Pattern.quote(visibleString), caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(item.name).find()) {
                return true;
            }
        }

        return false;
    }

    private boolean isItemHidden(final StorageItem item, final String[] hiddenList, final boolean caseSensitive) {
        if (hiddenList.length == 0 || (hiddenList.length == 1 && hiddenList[0].isEmpty())) return false;

        for (final String hiddenString : hiddenList) {
            final Pattern pattern = Pattern.compile(hiddenString, caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(item.name).find()) {
                return true;
            }
        }

        return false;
    }

    public void addOnChangeListener(final Runnable listener) {
        listeners.add(listener);
    }

    protected void notifyListeners() {
        for (final Runnable listener : listeners) {
            listener.run();
        }
    }

    public Optional<StorageItem> getItem(final int itemId) {
        return Optional.ofNullable(storage.get(itemId));
    }
}
