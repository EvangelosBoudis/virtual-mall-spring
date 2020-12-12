package com.nativeboyz.vmall.models;

import com.nativeboyz.vmall.models.dto.OrderedImage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderedActionImage extends OrderedImage {

    private ActionType actionType;

    public OrderedActionImage(OrderedImage dto, ActionType actionType) {
        super(dto.getName(), dto.getOrder());
        this.actionType = actionType;
    }

    public OrderedActionImage(String imageName, int priorityLevel, ActionType actionType) {
        super(imageName, priorityLevel);
        this.actionType = actionType;
    }

    public static List<OrderedImage> filter(List<OrderedActionImage> imageActions, ActionType type) {
        return filter(imageActions, new ActionType[] { type });
    }

    public static List<OrderedImage> filter(List<OrderedActionImage> imageActions, ActionType[] types) {
        List<ActionType> typesAsList = Arrays.asList(types);
        return imageActions.stream()
                .filter(x -> typesAsList.contains(x.actionType))
                .collect(Collectors.toList());
    }

    public static List<OrderedActionImage> merge(
            List<OrderedImage> productImages,
            List<String> existingImageNames
    ) {
        // Save & Update images
        List<OrderedActionImage> actions = productImages
                .stream()
                .map(provided -> {
                    boolean contains = existingImageNames.contains(provided.getName());
                    return new OrderedActionImage(provided, contains ? ActionType.UPDATE : ActionType.SAVE);
                })
                .collect(Collectors.toList());

        List<String> productImageNames = productImages
                .stream()
                .map(OrderedImage::getName)
                .collect(Collectors.toList());

        // Delete images
        List<OrderedActionImage> dActions = existingImageNames
                .stream()
                .filter(existingImage -> !productImageNames.contains(existingImage))
                .map(notContainedImage -> new OrderedActionImage(notContainedImage, -1, ActionType.DELETE))
                .collect(Collectors.toList());

        actions.addAll(dActions);
        return actions;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "OrderedActionImage{" +
                "actionType=" + actionType +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
