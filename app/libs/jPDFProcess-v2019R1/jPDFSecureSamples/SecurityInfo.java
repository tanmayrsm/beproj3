package jPDFSecureSamples;

import com.qoppa.pdf.permissions.PasswordPermissions;

public class SecurityInfo 
{
	String m_OpenPassword;
	String m_PermissionsPassword;
	PasswordPermissions m_Permissions;
	int m_Encryption;
	
	/**
	 * @return Returns the m_OpenPassword.
	 */
	public String getOpenPassword() {
		return m_OpenPassword;
	}
	/**
	 * @param openPassword The m_OpenPassword to set.
	 */
	public void setOpenPassword(String openPassword) {
		m_OpenPassword = openPassword;
	}
	/**
	 * @return Returns the m_PermissionsPassword.
	 */
	public String getPermissionsPassword() {
		return m_PermissionsPassword;
	}
	/**
	 * @param permissionsPassword The m_PermissionsPassword to set.
	 */
	public void setPermissionsPassword(String permissionsPassword) {
		m_PermissionsPassword = permissionsPassword;
	}
    /**
	 * @return Returns the Permission object.
	 */
    public PasswordPermissions getPermissions()
    {
        return m_Permissions;
    }
    /**
	 * Sets the Permission object.
     * 
	 * @param Permission object
	 */
    public void setPermissions(PasswordPermissions permissions)
    {
        m_Permissions = permissions;
    }
    
    public void setEncryption(int encryption) 
    {
    	m_Encryption = encryption;
    }
    
    public int getEncryption()
    {
    	return m_Encryption;
    }

}
