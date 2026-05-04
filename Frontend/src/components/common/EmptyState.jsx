import React from 'react';

const EmptyState = ({ title = 'No data found', message = 'There is nothing to display at the moment.', icon = '📁' }) => {
  return (
    <div className="empty-state">
      <div className="empty-state-icon">{icon}</div>
      <h3>{title}</h3>
      <p>{message}</p>
    </div>
  );
};

export default EmptyState;
