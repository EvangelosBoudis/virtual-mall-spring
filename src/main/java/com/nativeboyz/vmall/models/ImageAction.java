package com.nativeboyz.vmall.models;

import com.nativeboyz.vmall.models.dto.ProductImageDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImageAction extends ProductImageDto {

    private ActionType actionType;

    public ImageAction(String imageName, int priorityLevel, ActionType actionType) {
        super(imageName, priorityLevel);
        this.actionType = actionType;
    }

    public static List<ImageAction> filter(List<ImageAction> imageActions, ActionType type) {
        return filter(imageActions, new ActionType[] { type });
    }

    public static List<ImageAction> filter(List<ImageAction> imageActions, ActionType[] types) {
        List<ActionType> typesAsList = Arrays.asList(types);
        return imageActions.stream()
                .filter(x -> typesAsList.contains(x.actionType))
                .collect(Collectors.toList());
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return "ImageAction{" +
                "actionType=" + actionType +
                ", imageName='" + name + '\'' +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}
