import StorefrontIcon from "@mui/icons-material/Storefront";
import SchoolIcon from "@mui/icons-material/School";
import ChildCareIcon from "@mui/icons-material/ChildCare";
import ApartmentIcon from "@mui/icons-material/Apartment";
import DescriptionIcon from "@mui/icons-material/Description";
import LocalShippingIcon from "@mui/icons-material/LocalShipping";
import Inventory2Icon from "@mui/icons-material/Inventory2";

const IconMap = {
  Store: StorefrontIcon,
  School: SchoolIcon,
  HeartHandshake: ChildCareIcon,
  Building: ApartmentIcon,
  FileText: DescriptionIcon,
  Truck: LocalShippingIcon,
  PackageCheck: Inventory2Icon,
};

const PdsMetricsCard = ({ metric, onCardClick }) => {
  const IconComponent = IconMap[metric.iconName];

  return (
    <div className="w-full min-w-[220px] cursor-pointer bg-[#F1E4C3]/10 border border-[#228B22] backdrop-filter backdrop-blur-3xl p-3 rounded-lg flex flex-col justify-center h-full min-h-[120px] max-h-[150px] transition-transform duration-200 hover:-translate-y-1 hover:shadow-[#597E52] shadow-inner">
      <div>
        <div
          onClick={() => onCardClick(metric.label)}
          className="flex items-center justify-center mb-1"
        >
          {IconComponent && (
            <IconComponent
              sx={{
                color: "#228A22",
                fontSize: 17,
                mr: 1,
                mb: 0.5,
              }}
            />
          )}
          <h6 className="text-md font-medium text-[#1d291b] mb-1">
            {metric.label}
          </h6>
        </div>
        <p className="text-base text-center font-semibold text-[#1d291b]">
          {metric.value}
        </p>
      </div>
      {/* <button className="w-auto px-3 py-1 mt-2 text-xs font-semibold text-[#228B22] bg-[#F1E4C3]/50 rounded shadow-inner border border-[#228B22] transition-all duration-200 hover:bg-[#228B22] hover:text-white focus:outline-none focus:ring-2 focus:ring-[#228B22] focus:ring-offset-2 self-start">
        View More
      </button> */}
    </div>
  );
};

export default PdsMetricsCard;
