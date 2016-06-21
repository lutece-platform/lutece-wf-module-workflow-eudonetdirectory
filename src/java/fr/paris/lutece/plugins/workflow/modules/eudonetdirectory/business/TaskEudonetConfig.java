/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.business;

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;


/**
 * TaskCreatePDFConfig
 *
 */
public class TaskEudonetConfig extends TaskConfig
{
    @NotNull
    private int _nIdDirectory;
    @NotNull
    private String _strSessionKeyFamily;
    @NotNull
    private String _strSubscriberLogin;
    @NotNull
    private String _strSubscriberPassword;
    @NotNull
    private String _strUserLogin;
    @NotNull
    private String _strUserPassword;
    @NotNull
    private String _strBaseName;
    @NotNull
    private String _strWsUrl;
    private Collection<EudonetData> _entry;

    /**
     * @return the IdDirectory
     */
    public int getIdDirectory(  )
    {
        return _nIdDirectory;
    }

    /**
     * @param nIdDirectory the IdDirectory to set
     */
    public void setIdDirectory( int nIdDirectory )
    {
        _nIdDirectory = nIdDirectory;
    }

    /**
     *
     * @return the SessionKeyFamily
     */
    public String getSessionKeyFamily(  )
    {
        return _strSessionKeyFamily;
    }

    /**
     * @param sessionKeyFamily
     */
    public void setSessionKeyFamily( String sessionKeyFamily )
    {
        _strSessionKeyFamily = sessionKeyFamily;
    }

    /**
     * @return the _SubscriberLogin
     */
    public String getSubscriberLogin(  )
    {
        return _strSubscriberLogin;
    }

    /**
     * @param subscriberLogin
     */
    public void setSubscriberLogin( String subscriberLogin )
    {
        _strSubscriberLogin = subscriberLogin;
    }

    /**
     * @return SubscriberPassword
     */
    public String getSubscriberPassword(  )
    {
        return _strSubscriberPassword;
    }

    /**
     * @param subscriberPassword
     */
    public void setSubscriberPassword( String subscriberPassword )
    {
        _strSubscriberPassword = subscriberPassword;
    }

    /**
     * @return UserLogin
     */
    public String getUserLogin(  )
    {
        return _strUserLogin;
    }

    /**
     * @param userLogin
     */
    public void setUserLogin( String userLogin )
    {
        _strUserLogin = userLogin;
    }

    /**
     * @return UserPassword
     */
    public String getUserPassword(  )
    {
        return _strUserPassword;
    }

    /**
     * @param userPassword
     */
    public void setUserPassword( String userPassword )
    {
        _strUserPassword = userPassword;
    }

    /**
     * @return BaseName
     */
    public String getBaseName(  )
    {
        return _strBaseName;
    }

    /**
     * @param baseName
     */
    public void setBaseName( String baseName )
    {
        _strBaseName = baseName;
    }

    /**
     * @return WsUrl
     */
    public String getWsUrl(  )
    {
        return _strWsUrl;
    }

    /**
     * @param wsUrl
     */
    public void setWsUrl( String wsUrl )
    {
        _strWsUrl = wsUrl;
    }

    /**
     * @return entry list
     */
    public Collection<EudonetData> getEntry(  )
    {
        return _entry;
    }

    /**
     * @param entry
     */
    public void setEntry( Collection<EudonetData> entry )
    {
        _entry = entry;
    }
}
