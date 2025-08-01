import React, { useEffect, useState } from "react";

const PdsHeader = () => {
  const images = [
    "https://images.unsplash.com/photo-1700906310080-65a7d9718929?q=80&w=1632&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1615829254885-d4bfd5ce700e?q=80&w=1286&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1746106434965-da8cdec51da6?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1628352081506-83c43123ed6d?q=80&w=1296&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
  ];

  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 2000);
    return () => clearInterval(interval);
  }, [images.length]);

  return (
    <div className="mb-10 text-center flex flex-col items-center justify-center">
      {/* <div className="w-[62%] h-[29vh] rounded overflow-hidden shadow-lg shadow-[#597E52]">
        <img
          src={images[currentImageIndex]}
          alt=""
          className="w-full h-full object-cover transition-opacity duration-500 ease-in-out"
          onError={(e) => {
            e.target.onerror = null;
            e.target.src =
              "https://placehold.co/1200x200/cccccc/333333?text=Image+Unavailable";
          }}
        />
      </div> */}
      <h1 className="text-3xl font-bold font-sans text-[#1d291b] mt-6 sm:text-4xl">
        PDS Dashboard - June ' 2025
      </h1>
    </div>
  );
};

export default PdsHeader;
