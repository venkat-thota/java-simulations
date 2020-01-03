
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IParameterKey;

/**
 * @author Sam Reid
 */
public enum ParameterKeys implements IParameterKey {
    canvasPositionX, canvasPositionY, componentType, description, height, interactive, item,
    isSelected, key, part, text, title, value, width, window, x, y, z, type,
    angle,
    enabled,
    minX, maxX, averageX, minY, maxY, averageY,
    isPlaying,
    selectedKit,

    //For system:
    time, name, version, project, flavor, locale, distributionTag, javaVersion, osName, osVersion,
    parserVersion, study, id, machineCookie, messageCount, messageIndex, sessionId, commandLineArgs,

    errorMessage,
    //For drag events
    numberDragEvents, minValue, maxValue, averageValue,

    correctedValue,
    ipAddress,
    mountainTime,
    errorType,
    commitAction,
    x2, y2, wavelength
}
