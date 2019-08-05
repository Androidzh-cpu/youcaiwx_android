package com.ucfo.youcai.utils;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Author:29117
 * Time: 2019-3-20.  下午 6:31
 * Email:2911743255@qq.com
 * ClassName: AsteriskPasswordTransformationMethod
 * Package: com.ucfo.youcai.utils
 * Description:TODO  输入密码变为***
 * Detail:
 */
public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        public char charAt(int index) {
            return '*'; // This is the important part
        }

        public int length() {
            return mSource.length(); // Return default
        }

        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }

    }
}
