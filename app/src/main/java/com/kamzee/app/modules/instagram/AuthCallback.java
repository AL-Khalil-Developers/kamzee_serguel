package com.kamzee.app.modules.instagram;

import com.kamzee.app.models.others.InstagramUser;

public interface AuthCallback {
  void onSuccess(InstagramUser socialUser);

  void onError(Throwable error);

  void onCancel();
}
