
package com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages;

import com.aimxcel.abclearn.common.aimxcelcommon.simsharing.messages.IUserComponent;

public enum UserComponents implements IUserComponent {
    playPauseButton, tab, stepButton, stepBackButton, rewindButton,
    sponsorDialog, askDialog, dataCollectionLogMenuItem,
    resetAllConfirmationDialogYesButton, resetAllConfirmationDialogNoButton,
    nextButton, previousButton,

    showLogs,
    simSharingLogFileDialog,
    fileChooserCancelButton, fileChooserSaveButton, replaceFileNoButton, replaceFileYesButton, saveButton,

    aimxcelFrame,
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
