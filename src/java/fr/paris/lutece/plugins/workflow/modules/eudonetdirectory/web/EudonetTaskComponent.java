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
package fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.web;

import fr.paris.lutece.plugins.directory.business.DirectoryHome;
import fr.paris.lutece.plugins.directory.business.IEntry;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.plugins.directory.utils.DirectoryUtils;
import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.business.EudonetData;
import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.business.TaskEudonetConfig;
import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.business.TaskEudonetConfigHome;
import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.service.EudonetWsEudonet;
import fr.paris.lutece.plugins.workflow.modules.eudonetdirectory.utils.EudonetDirctoryConstants;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * EudonetTaskComponent
 *
 */
public class EudonetTaskComponent extends NoFormTaskComponent
{
    // MARKS
    private static final String MARKER_TASK_EUDONET_CONFIG = "taskConfig";
    private static final String MARKER_LIST_ATTRIBUT_EUDONET = "list_attribut";
    private static final String MARKER_ENTRY = "entries";

    // TEMPLATES
    private static final String TEMPLATE_TASK_EUDONET = "admin/plugins/workflow/modules/eudonetdirectory/task_export_eudonet_config.html";

    // PARAMETERS
    public static final String PARAMETER_ID_DIRECTORE = "id_directory";
    public static final String PARAMETER_SESSION_KEY = "session_key_family";
    public static final String PARAMETER_SUBSCRIBER_LOGIN = "subscriber_login";
    public static final String PARAMETER_SUBSCRIBER_PASSWORD = "subscriber_password";
    public static final String PARAMETER_USER_LOGIN = "user_login";
    public static final String PARAMETER_USER_PASSWORD = "user_password";
    public static final String PARAMETER_BASE_NAME = "base_name";
    public static final String PARAMETER_WSDL_URL = "wsdl_url";
    public static final String PARAMETER_CREAT_ENTRY = "apply";
    public static final String PARAMETER_DELETE_ENTRY = "deleteEntry";
    public static final String PARAMETER_CREATE_ENTRY = "createEntry";
    public static final String PARAMETER_ORDER_ENTRY = "order_entry";
    public static final String PARAMETER_EUDONET_ATTRIBUT = "eudonet_attribut";
    public static final String PARAMETER_ID_ENTRY = "id_entry";

    // SERVICES
    @Inject
    @Named( EudonetDirctoryConstants.BEAN_EUDONET_DIRECTORY_CONFIG_SERVICE )
    private ITaskConfigService _taskEudonetConfigService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String doSaveConfig( HttpServletRequest request, Locale locale, ITask task )
    {
        String idDirectory = request.getParameter( PARAMETER_ID_DIRECTORE );
        String sessionKeyFamily = request.getParameter( PARAMETER_SESSION_KEY );
        String subscriberLogin = request.getParameter( PARAMETER_SUBSCRIBER_LOGIN );
        String subscriberPassword = request.getParameter( PARAMETER_SUBSCRIBER_PASSWORD );
        String userLogin = request.getParameter( PARAMETER_USER_LOGIN );
        String userPassword = request.getParameter( PARAMETER_USER_PASSWORD );
        String baseName = request.getParameter( PARAMETER_BASE_NAME );
        String wsdlUrl = request.getParameter( PARAMETER_WSDL_URL );

        TaskEudonetConfig config = _taskEudonetConfigService.findByPrimaryKey( task.getId(  ) );
        Boolean bCreate = false;

        if ( config == null )
        {
            config = new TaskEudonetConfig(  );
            config.setIdTask( task.getId(  ) );
            bCreate = true;
        }

        if ( ( ( request.getParameter( PARAMETER_CREAT_ENTRY ) != null ) &&
                PARAMETER_CREATE_ENTRY.equals( request.getParameter( PARAMETER_CREAT_ENTRY ) ) ) &&
                ( Integer.parseInt( idDirectory ) != WorkflowUtils.CONSTANT_ID_NULL ) )
        {
            String ordreEntry = request.getParameter( PARAMETER_ORDER_ENTRY );
            String eudonetAttribut = request.getParameter( PARAMETER_EUDONET_ATTRIBUT );

            EudonetData data = new EudonetData(  );
            data.setIdConfig( task.getId(  ) );
            data.setOrderEntry( Integer.parseInt( ordreEntry ) );
            data.setEudonetAttribut( eudonetAttribut );

            TaskEudonetConfigHome.creatEntry( data );

            return null;
        }
        else if ( ( request.getParameter( PARAMETER_ID_ENTRY ) != null ) &&
                ( request.getParameter( PARAMETER_CREAT_ENTRY ) != null ) &&
                PARAMETER_DELETE_ENTRY.equals( request.getParameter( PARAMETER_CREAT_ENTRY ) ) &&
                ( Integer.parseInt( idDirectory ) != WorkflowUtils.CONSTANT_ID_NULL ) )
        {
            String idEntry = request.getParameter( PARAMETER_ID_ENTRY );
            TaskEudonetConfigHome.deleteEntry( Integer.parseInt( idEntry ) );

            return null;
        }

        config.setIdDirectory( Integer.parseInt( idDirectory ) );
        config.setSessionKeyFamily( sessionKeyFamily );
        config.setSubscriberLogin( subscriberLogin );
        config.setSubscriberPassword( subscriberPassword );
        config.setUserLogin( userLogin );
        config.setUserPassword( userPassword );
        config.setBaseName( baseName );
        config.setWsUrl( wsdlUrl );

        if ( bCreate )
        {
            _taskEudonetConfigService.create( config );
        }
        else
        {
            _taskEudonetConfigService.update( config );
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        String strIdTask = request.getParameter( EudonetDirctoryConstants.PARAMETER_ID_TASK );
        TaskEudonetConfig taskEudonetConfig = null;

        int nIdDirectory;

        if ( StringUtils.isNotBlank( request.getParameter( EudonetDirctoryConstants.PARAMETER_ID_DIRECTORY ) ) )
        {
            nIdDirectory = DirectoryUtils.convertStringToInt( request.getParameter( 
                        EudonetDirctoryConstants.PARAMETER_ID_DIRECTORY ) );
        }
        else
        {
            nIdDirectory = -1;
        }

        if ( StringUtils.isNotBlank( strIdTask ) )
        {
            taskEudonetConfig = _taskEudonetConfigService.findByPrimaryKey( DirectoryUtils.convertStringToInt( 
                        strIdTask ) );

            if ( taskEudonetConfig != null )
            {
                model.put( MARKER_TASK_EUDONET_CONFIG, taskEudonetConfig );

                List<EudonetData> entries = (List<EudonetData>) taskEudonetConfig.getEntry(  );

                model.put( MARKER_ENTRY, entries );
                nIdDirectory = taskEudonetConfig.getIdDirectory(  );
            }
            else
            {
                model.put( MARKER_TASK_EUDONET_CONFIG, new TaskEudonetConfig(  ) );
            }
        }

        model.put( EudonetDirctoryConstants.MARK_DIRECTORY_LIST, getListDirectories(  ) );
        model.put( EudonetDirctoryConstants.MARK_LIST_ENTRIES, getListEntries( nIdDirectory, request ) );
        model.put( MARKER_LIST_ATTRIBUT_EUDONET, getEudonetAttribut( taskEudonetConfig ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_EUDONET, locale, model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
    * Get the list of directorise
    * @return a ReferenceList
    */
    private static ReferenceList getListDirectories(  )
    {
        Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
        ReferenceList listDirectories = DirectoryHome.getDirectoryList( pluginDirectory );
        ReferenceList refenreceListDirectories = new ReferenceList(  );
        refenreceListDirectories.addItem( DirectoryUtils.CONSTANT_ID_NULL, StringUtils.EMPTY );

        if ( listDirectories != null )
        {
            refenreceListDirectories.addAll( listDirectories );
        }

        return refenreceListDirectories;
    }

    /**
     * Method to get directory entries list
     * @param nIdDirectory id directory
     * @param request request
     * @return ReferenceList entries list
     */
    private static ReferenceList getListEntries( int nIdDirectory, HttpServletRequest request )
    {
        if ( nIdDirectory != -1 )
        {
            Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
            List<IEntry> listEntries = DirectoryUtils.getFormEntries( nIdDirectory, pluginDirectory,
                    AdminUserService.getAdminUser( request ) );
            ReferenceList referenceList = new ReferenceList(  );

            for ( IEntry entry : listEntries )
            {
                if ( entry.getEntryType(  ).getComment(  ) )
                {
                    continue;
                }

                if ( entry.getEntryType(  ).getGroup(  ) )
                {
                    if ( entry.getChildren(  ) != null )
                    {
                        for ( IEntry child : entry.getChildren(  ) )
                        {
                            if ( child.getEntryType(  ).getComment(  ) )
                            {
                                continue;
                            }

                            ReferenceItem referenceItem = new ReferenceItem(  );
                            referenceItem.setCode( String.valueOf( child.getIdEntry(  ) ) );
                            referenceItem.setName( child.getTitle(  ) );
                            referenceList.add( referenceItem );
                        }
                    }
                }
                else
                {
                    ReferenceItem referenceItem = new ReferenceItem(  );
                    referenceItem.setCode( String.valueOf( entry.getIdEntry( ) ) );
                    referenceItem.setName( entry.getTitle(  ) );
                    referenceList.add( referenceItem );
                }
            }

            return referenceList;
        }
        else
        {
            return new ReferenceList(  );
        }
    }

    /**
     * Method to get eudonet attributs list
     * @param taskEudonetConfig
     * @return attributs list
     */
    private static ReferenceList getEudonetAttribut( TaskEudonetConfig taskEudonetConfig )
    {
        ReferenceList referenceList = new ReferenceList(  );

        Set<String> listEntry = EudonetWsEudonet.getAttributEudonet(  );

        for ( String entry : listEntry )
        {
            boolean isContain = false;

            if ( taskEudonetConfig != null )
            {
                for ( EudonetData ent : taskEudonetConfig.getEntry(  ) )
                {
                    if ( entry.equals( ent.getEudonetAttribut(  ) ) )
                    {
                        isContain = true;

                        break;
                    }
                }
            }

            if ( !isContain )
            {
                ReferenceItem referenceItem = new ReferenceItem(  );
                referenceItem.setCode( entry );
                referenceItem.setName( entry );
                referenceList.add( referenceItem );
            }
        }

        return referenceList;
    }
}
