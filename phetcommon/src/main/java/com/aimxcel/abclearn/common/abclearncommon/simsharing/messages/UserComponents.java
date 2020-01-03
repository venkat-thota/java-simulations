
package com.aimxcel.abclearn.common.abclearncommon.simsharing.messages;

import com.aimxcel.abclearn.common.abclearncommon.simsharing.messages.IUserComponent;

/**
 * Enum for components manipulated by the user.
 */
public enum UserComponents implements IUserComponent {
    playPauseButton, tab, stepButton, stepBackButton, rewindButton,
    sponsorDialog, askDialog, dataCollectionLogMenuItem,
    resetAllConfirmationDialogYesButton, resetAllConfirmationDialogNoButton,
    nextButton, previousButton,

    showLogs,
    simSharingLogFileDialog,
    fileChooserCancelButton, fileChooserSaveButton, replaceFileNoButton, replaceFileYesButton, saveButton,

    phetFrame,
    fileMenu, exitMenuItem,
    helpMenu, helpMenuItem, megaHelpMenuItem, aboutMenuItem, checkForSimulationUpdateMenuItem,
    saveMenuItem, loadMenuItem, preferencesMenuItem,

    optionsMenu,
    teacherMenu,
    resetAllButton,

    faucetImage, slider,
    spinner,

    //chained with other features, used in Energy Skate Park but declared here for reusability elsewhere
    onRadioButton,
    offRadioButton,

    conductivityTester, positiveProbe, negativeProbe,

    heaterCoolerSlider,

    aboutDialogCloseButton, aboutDialogCreditsButton,
    aboutDialogSoftwareAgreementButton,
    icon,

    helpButton,
    hideHelpButton
}
