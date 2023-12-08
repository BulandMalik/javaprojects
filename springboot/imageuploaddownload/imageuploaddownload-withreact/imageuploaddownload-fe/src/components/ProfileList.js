import React from 'react';
import deleteIcon from '../delete.png';

const ProfileList = ({ profiles, onDocumentClick }) => {
  return (<>
    <div>
      <h2>Profile List</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Profile Id</th>
            <th>Profile Name</th>
            <th>Storage Quota</th>
            <th>Documents</th>
          </tr>
        </thead>
        <tbody>
          {profiles.map(profile => (
            <tr key={profile.profileId}>
              <td>{profile.profileId}</td>
              <td>{profile.profileName}</td>
              <td>{profile.profileDocumentStorageQuota}</td>
              <td>
                <ul>
                  {profile.profileDocuments?.map(document => (<>
                        <div>
                            <li key={document.profileDocumentAccessURI}>
                                <a
                                    href="#"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        onDocumentClick(profile.profileId, document.profileDocumentAccessURI, false);
                                    }}
                                >
                                    {document.profileDocumentAccessURI}
                                </a>
                                <span>&nbsp;&nbsp;</span>
                                <a
                                    href="#"
                                    onClick={(e) => {
                                        e.preventDefault();
                                        onDocumentClick(profile.profileId, document.profileDocumentAccessURI, true);
                                    }}
                                >
                                    <span><img src={deleteIcon} alt="Delete"/></span>
                                </a>                                
                            </li>
                        </div>
                    </>
                ))}
                </ul>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>  
  </>
  );
};

export default ProfileList;