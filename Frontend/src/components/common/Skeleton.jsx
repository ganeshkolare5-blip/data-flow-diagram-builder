import React from 'react';

const Skeleton = ({ width = '100%', height = '1rem', borderRadius = '4px', className = '' }) => {
  return (
    <div 
      className={`skeleton-loader ${className}`}
      style={{
        width,
        height,
        borderRadius,
        backgroundColor: 'rgba(255, 255, 255, 0.05)',
        position: 'relative',
        overflow: 'hidden'
      }}
    >
      <div className="skeleton-shimmer" />
    </div>
  );
};

export default Skeleton;
