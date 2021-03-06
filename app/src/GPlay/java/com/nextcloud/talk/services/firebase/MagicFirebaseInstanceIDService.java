/*
 * Nextcloud Talk application
 *
 * @author Mario Danic
 * Copyright (C) 2017 Mario Danic <mario@lovelyhq.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.nextcloud.talk.services.firebase;

import com.evernote.android.job.JobRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.nextcloud.talk.application.NextcloudTalkApplication;
import com.nextcloud.talk.jobs.PushRegistrationJob;
import com.nextcloud.talk.utils.preferences.AppPreferences;

import javax.inject.Inject;

import autodagger.AutoInjector;

@AutoInjector(NextcloudTalkApplication.class)
public class MagicFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MagicFirebaseInstanceIDService";

    @Inject
    AppPreferences appPreferences;

    public MagicFirebaseInstanceIDService() {
        super();
        NextcloudTalkApplication.getSharedApplication().getComponentApplication()
                .inject(this);
    }

    @Override
    public void onTokenRefresh() {
        appPreferences.setPushToken(FirebaseInstanceId.getInstance().getToken());
        new JobRequest.Builder(PushRegistrationJob.TAG).setUpdateCurrent(true).startNow().build().schedule();
    }
}
