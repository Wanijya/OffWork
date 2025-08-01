import React, { useState } from "react";
// Update the import path and the hook name
import usePdsDashboardData from "../hooks/usePdsDashboardData"; // Fixed: Renamed hook import
import PdsLoadingSpinner from "../components/PdsLoadingSpinner";
import PdsHeader from "../components/PdsHeader";
import PdsMetricsCard from "../components/PdsMetricsCard";
import PdsErrorMessage from "../components/PdsErrorMessage";
import PdsCardDetailModal from "../components/PdsCardDetailModal";
import { useAuth } from "../context/AuthContext";
import LogoutIcon from "@mui/icons-material/Logout";

// Main Dashboard Page Component
const DashboardPage = () => {
  const { logout, userRole, userDistCode, authMessage } = useAuth();

  // Use the renamed hook
  const { data, loading, error } = usePdsDashboardData(userRole, userDistCode); // Fixed: Use renamed hook

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedCardType, setSelectedCardType] = useState(null);
  const [selectedCardLabel, setSelectedCardLabel] = useState(null);

  const metrics = data
    ? [
        {
          label: "Total Shops",
          value: data.totalShops,
          iconName: "Store",
          tooltipText: "All registered fair price shops.",
        },
        {
          label: "Active Shops",
          value: data.activeShops,
          iconName: "Store",
          tooltipText: "Currently active fair price shops.",
          visualizationType: "bar",
        },
        {
          label: "Total MDM Entries",
          value: data.totalMdmEntries,
          iconName: "School",
          tooltipText: "Total Mid-Day Meal entries.",
          visualizationType: "line",
        },
        {
          label: "Total ICDS Entries",
          value: data.totalIcdsEntries,
          iconName: "ChildCare",
          tooltipText: "Total Integrated Child Development Services entries.",
          visualizationType: "line",
        },
        {
          label: "Total Welfare Institutes",
          value: data.totalWelfareInstitutes,
          iconName: "Building",
          tooltipText: "Total registered welfare institutes.",
          visualizationType: "bar",
        },
        {
          label: "Total RO Generated",
          value: data.totalRoGenerated,
          iconName: "FileText",
          tooltipText: "Total Release Orders generated.",
          visualizationType: "line",
        },
        {
          label: "Total Dispatched Qty",
          value: data.totalDispatchedQuantity,
          iconName: "Truck",
          tooltipText: "Total quantity of commodities dispatched.",
          visualizationType: "bar",
        },
        {
          label: "Total Received Qty",
          value: data.totalReceivedQuantity,
          iconName: "PackageCheck",
          tooltipText: "Total quantity of commodities received.",
          visualizationType: "bar",
        },
      ]
    : [];

  if (loading || userRole === null || userDistCode === null) {
    return <PdsLoadingSpinner />;
  }

  if (error) {
    return <PdsErrorMessage error={error} />;
  }

  const handleCardClick = (metricLabel) => {
    setSelectedCardType(metricLabel);
    setSelectedCardLabel(metricLabel);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setSelectedCardType(null);
    setSelectedCardLabel(null);
  };

  return (
    <div className="min-h-screen p-6 font-sans">
      <div className="w-full px-4 py-3 rounded-lg flex flex-col md:flex-row items-center justify-between gap-0 md:gap-4">
        {/* Header */}
        <div className="w-full text-center md:w-auto md:flex-1">
          <PdsHeader />
        </div>

        {/* Logout Button */}
        <button
          onClick={logout}
          className="px-3 py-1 md:px-5 md:py-2 md:mb-3  bg-red-600 text-white text-sm font-normal rounded-lg shadow-md hover:bg-red-700 transition duration-300 ml-40 md:ml-auto"
        >
          <LogoutIcon fontSize="small" />
          {/* ({userRole ? userRole.replace('ROLE_', '') : 'User'}) */}
        </button>
      </div>
      {authMessage && (
        <div
          className={`text-center mb-4 p-2 rounded-md ${
            authMessage.type === "error"
              ? "bg-red-100 text-red-700"
              : "bg-green-100 text-green-700"
          }`}
        >
          {authMessage.text}
        </div>
      )}
      <div className="w-[90%] flex flex-col items-center justify-center mx-auto mt-2">
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {metrics.map((metric, index) => (
            <PdsMetricsCard
              key={index}
              metric={metric}
              onCardClick={() => handleCardClick(metric.label)}
            />
          ))}
        </div>
      </div>
      <div className="mt-10 text-right mr-16 sm:mr-56 text-gray-600 text-sm">
        <span>Last Refreshed: {data.lastRefreshed}</span>
      </div>
      <PdsCardDetailModal
        open={isModalOpen}
        handleClose={handleModalClose}
        cardType={selectedCardType}
        cardLabel={selectedCardLabel}
        userDistCode={userDistCode}
        userRole={userRole}
      />
    </div>
  );
};

export default DashboardPage;
