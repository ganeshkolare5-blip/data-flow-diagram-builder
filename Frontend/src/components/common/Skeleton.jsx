import React from 'react';
import './Skeleton.css';

const Skeleton = ({ width, height, borderRadius = '4px', className = '' }) => {
  return (
    <div 
      className={`skeleton-item ${className}`}
      style={{ width, height, borderRadius }}
    ></div>
  );
};

export const DiagramSkeleton = () => (
  <div className="diagram-skeleton-card">
    <Skeleton width="60%" height="24px" className="mb-2" />
    <Skeleton width="100%" height="16px" className="mb-1" />
    <Skeleton width="90%" height="16px" className="mb-1" />
    <div className="diagram-skeleton-footer">
        <Skeleton width="30%" height="12px" />
        <Skeleton width="20%" height="12px" />
    </div>
  </div>
);

export default Skeleton;
