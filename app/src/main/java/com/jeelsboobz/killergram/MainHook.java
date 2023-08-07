package com.jeelsboobz.killergram;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public final static List<String> hookPackages = Arrays.asList(
            "org.telegram.messenger",
            "org.telegram.messenger.web",
            "org.telegram.messenger.beta",
            "nekox.messenger",
            "com.cool2645.nekolite",
            "org.telegram.plus",
            "com.iMe.android",
            "org.telegram.BifToGram",
            "ua.itaysonlab.messenger",
            "org.forkclient.messenger",
            "org.forkclient.messenger.beta",
            "org.aka.messenger",
            "ellipi.messenger",
            "org.nift4.catox",
            "it.owlgram.android",
            "me.ninjagram.messenger",
            "ir.ilmili.telegraph");

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (hookPackages.contains(lpparam.packageName)) {
            try {
                Class<?> messagesControllerClass = XposedHelpers.findClassIfExists("org.telegram.messenger.MessagesController", lpparam.classLoader);
                if (messagesControllerClass != null) {
                    XposedBridge.hookAllMethods(messagesControllerClass, "getSponsoredMessages", XC_MethodReplacement.returnConstant(null));
                    XposedBridge.hookAllMethods(messagesControllerClass, "isChatNoForwards", XC_MethodReplacement.returnConstant(false));
                }
                Class<?> chatUIActivityClass = XposedHelpers.findClassIfExists("org.telegram.ui.ChatActivity", lpparam.classLoader);
                if (chatUIActivityClass != null) {
                    XposedBridge.hookAllMethods(chatUIActivityClass, "addSponsoredMessages", XC_MethodReplacement.returnConstant(null));
                }
                Class<?> SharedConfigClass = XposedHelpers.findClassIfExists("org.telegram.messenger.SharedConfig", lpparam.classLoader);
                if (SharedConfigClass != null) {
                    XposedBridge.hookAllMethods(SharedConfigClass, "getDevicePerformanceClass", XC_MethodReplacement.returnConstant(2));
                }
                Class<?> UserConfigClass = XposedHelpers.findClassIfExists("org.telegram.messenger.UserConfig", lpparam.classLoader);
                if (UserConfigClass != null) {
                    XposedBridge.hookAllMethods(UserConfigClass, "getMaxAccountCount", XC_MethodReplacement.returnConstant(999));
                    XposedBridge.hookAllMethods(UserConfigClass, "isPremium", XC_MethodReplacement.returnConstant(true));
                }
                if (lpparam.packageName.equals("ir.ilmili.telegraph")) {
                    Class<?> DonateTelegraph = XposedHelpers.findClassIfExists("org.telegram.tgnet.ConnectionsManager", lpparam.classLoader);
                    if (DonateTelegraph != null) {
                        XposedBridge.hookAllMethods(DonateTelegraph, "native_expireFile", XC_MethodReplacement.returnConstant(false));
                        XposedBridge.hookAllMethods(DonateTelegraph, "native_daysFile", XC_MethodReplacement.returnConstant(999));
                        XposedBridge.hookAllMethods(DonateTelegraph, "native_removeInstance", XC_MethodReplacement.returnConstant(true));
                        XposedBridge.hookAllMethods(DonateTelegraph, "native_checkLicense", XC_MethodReplacement.returnConstant(true));
                    }
                    Class<?> MessagesControllerTelegraph = XposedHelpers.findClassIfExists("org.telegram.messenger.cb0", lpparam.classLoader);
                    if (MessagesControllerTelegraph != null) {
                        XposedBridge.hookAllMethods(MessagesControllerTelegraph, "ca", XC_MethodReplacement.returnConstant(false));
                    }
                    Class<?> UserConfigTelegraph = XposedHelpers.findClassIfExists("org.telegram.messenger.cb0", lpparam.classLoader);
                    if (UserConfigTelegraph != null) {
                        XposedBridge.hookAllMethods(UserConfigTelegraph, "pa", XC_MethodReplacement.returnConstant(true));
                    }
                }
            } catch (Throwable ignored) {
            }
        }
    }
}
