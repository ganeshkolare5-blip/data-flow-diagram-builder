import React from 'react';

const EmptyState = ({ title = "No data found", message = "There is nothing to display here at the moment.", action }) => {
  return (
    <div className="empty-state">
      <div className="empty-state-icon">📂</div>
      <h3>{title}</h3>
      <p>{message}</p>
      {action && (
        <button className="empty-state-action" onClick={action.onClick}>
          {action.label}
        </button>
      )}
    </div>
  );
};

export default EmptyState;
