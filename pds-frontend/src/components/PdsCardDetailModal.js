import apiClient from "../services/api";
import { useEffect, useState } from "react";
// import TestApiClient from "../services/TestApiClient";

const PdsCardDetailModal = ({ open, handleClose, cardType, cardLabel }) => {
  const apiEndpoints = {
    "Total Shops": "/details/shops-by-district",
    "Active Shops": "/details/shops-by-district",
    "Total MDM Entries": "/details/mdm-by-district",
    "Total ICDS Entries": "/details/icds-by-district",
    "Total Welfare Institutes": "/details/welfare-by-district",
    "Total RO Generated": "/details/ro-generated-by-district",
    "Total Dispatched Qty": "/details/dispatched-by-district",
    "Total Received Qty": "/details/received-by-district",
  };

  const [details, setDetails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // const { getData } = TestApiClient();

  useEffect(() => {
    if (open && cardType && apiEndpoints[cardType]) {
      const fetchDetails = async () => {
        setLoading(true);
        setError(null);
        try {
          const endpoint = apiEndpoints[cardType];
          const responseData = await apiClient.get(endpoint);
          setDetails(responseData.data);
        } catch (err) {
          console.error(`Failed to fetch details for ${cardType}:`, err);
          setError("Failed to load details. Please try again.");
        } finally {
          setLoading(false);
        }
      };
      fetchDetails();
    } else if (!open) {
      setDetails([]);
      setLoading(true);
      setError(null);
    }
  }, [open, cardType]);

  const getTableHeaders = () => {
    switch (cardType) {
      case "Total Shops":
      case "Active Shops":
        return ["District Name", "Total Shops"];
      case "Total MDM Entries":
        return ["District Name", "Total MDM Entries"];
      case "Total ICDS Entries":
        return ["District Name", "Total ICDS Entries"];
      case "Total Welfare Institutes":
        return ["District Name", "Total Welfare Institutes"];
      case "Total RO Generated":
        return ["District Name", "Total RO Generated"];
      case "Total Dispatched Qty":
        return ["District Name", "Total Dispatched (Kg)"];
      case "Total Received Qty":
        return ["District Name", "Total Received (Kg)"];
      default:
        return ["Details"];
    }
  };

  const getValueKey = () => {
    switch (cardType) {
      case "Total Shops":
      case "Active Shops":
        return "totalShops";
      case "Total MDM Entries":
        return "totalMdmEntries";
      case "Total ICDS Entries":
        return "totalIcdsEntries";
      case "Total Welfare Institutes":
        return "totalWelfareInstitutes";
      case "Total RO Generated":
        return "totalRoGenerated";
      case "Total Dispatched Qty":
        return "totalDispatchedQty";
      case "Total Received Qty":
        return "totalReceivedQty";
      default:
        return null;
    }
  };

  const getTableRows = () => {
    if (!details || details.length === 0) {
      return (
        <tr>
          <td
            colSpan={getTableHeaders().length}
            className="text-center py-2 border border-gray-300"
          >
            No data available.
          </td>
        </tr>
      );
    }

    const valueKey = getValueKey();

    return details.map((detail, index) => {
      let valueToDisplay = detail[valueKey];
      if (
        cardType === "Total Dispatched Qty" ||
        cardType === "Total Received Qty"
      ) {
        const numericValue = parseFloat(valueToDisplay);
        valueToDisplay = !isNaN(numericValue) ? numericValue.toFixed(2) : "N/A";
      }

      return (
        <tr
          key={index}
          className="hover:bg-[#d7da96d0] transition-colors duration-200 border border-gray-900"
        >
          <td className="px-4 py-2 border border-gray-900">
            {detail.districtName}
          </td>
          <td className="px-4 py-2 text-right border border-gray-900">
            {valueToDisplay}
          </td>
        </tr>
      );
    });
  };

  if (!open) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-[#1a2236]/60">
      <div className="bg-[#F0F0D7] rounded-lg shadow-lg w-full max-w-2xl mx-4">
        <div className="px-6 py-4 border-b border-[#878787]">
          <h2 className="text-xl font-semibold text-[#1a2236]">
            {cardLabel} - District-wise Details
          </h2>
        </div>
        <div className="px-7 py-4 max-h-[60vh] overflow-y-auto custom-scrollbar">
          {loading ? (
            <div className="flex items-center justify-center min-h-[200px]">
              <svg
                className="animate-spin h-6 w-6 text-[#2e5bff]"
                viewBox="0 0 24 24"
              >
                <circle
                  className="opacity-25"
                  cx="12"
                  cy="12"
                  r="10"
                  stroke="currentColor"
                  strokeWidth="4"
                  fill="none"
                />
                <path
                  className="opacity-75"
                  fill="currentColor"
                  d="M4 12a8 8 0 018-8v8z"
                />
              </svg>
              <span className="ml-2 text-[#1a2236]">Loading details...</span>
            </div>
          ) : error ? (
            <div className="text-[#ff3b30] text-center py-2">{error}</div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full border border-[#ddd7d7] rounded">
                <thead>
                  <tr>
                    {getTableHeaders().map((header, index) => (
                      <th
                        key={index}
                        className="px-4 py-2 font-bold bg-[#d7da96d0] text-[#1a2236] border border-gray-900"
                      >
                        {header}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody>{getTableRows()}</tbody>
              </table>
            </div>
          )}
        </div>
        <div className="px-6 py-4 border-t border-[#878787] flex justify-end">
          <button
            onClick={handleClose}
            className="px-3 py-1 mt-2 text-xs font-semibold text-[#228B22] bg-[#F1E4C3]/50 rounded shadow-inner border border-[#228B22] transition-all duration-200 hover:bg-[#228B22] hover:text-white focus:outline-none focus:ring-2 focus:ring-[#228B22] focus:ring-offset-2"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default PdsCardDetailModal;
